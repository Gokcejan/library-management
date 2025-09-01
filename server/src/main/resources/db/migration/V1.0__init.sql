-- Sekvence pro generování ID (podle @SequenceGenerator(sequenceName="table_generator"))
create sequence if not exists table_generator
    increment by 1
    minvalue 1
    start with 1
    cache 1;

-- =========================
--  AUTHOR
-- =========================
create table author
(
    id           bigint not null
        constraint author_pkey primary key,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    created_at   timestamp    not null default now(),
    updated_at   timestamp
);

-- =========================
--  PUBLISHER
-- =========================
create table publisher
(
    id           bigint not null
        constraint publisher_pkey primary key,
    name         varchar(255) not null,
    created_at   timestamp    not null default now(),
    updated_at   timestamp
);

-- =========================
--  LIBRARY_USER
-- =========================
create table library_user
(
    id           bigint not null
        constraint library_user_pkey primary key,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    email        varchar(255) not null,
    phone        varchar(255),
    username     varchar(255) not null,
    password     varchar(255) not null,
    role         varchar(50)  not null,      -- Enum as STRING
    created_at   timestamp    not null default now(),
    updated_at   timestamp,
    constraint uk_library_user_email unique (email),
    constraint uk_library_user_username unique (username)
);

-- =========================
--  BOOK
-- =========================
create table book
(
    id              bigint not null
        constraint book_pkey primary key,
    book_title      varchar(255) not null,
    status          varchar(50)  not null,   -- Enum as STRING (AVAILABLE, BORROWED, ...)
    created_at      timestamp    not null default now(),
    updated_at      timestamp,
    author_id       bigint       not null,
    publisher_id    bigint       not null,

    constraint fk_book_author
        foreign key (author_id) references author (id),

    constraint fk_book_publisher
        foreign key (publisher_id) references publisher (id)
);

create index idx_book_author     on book (author_id);
create index idx_book_publisher  on book (publisher_id);

-- =========================
--  BORROW
-- =========================
create table borrow
(
    id            bigint not null
        constraint borrow_pkey primary key,
    borrow_date   timestamp    not null,
    return_date   timestamp,
    due_date      timestamp,
    status        varchar(50)  not null,     -- Enum as STRING (BORROWED, RETURNED, ...)
    created_at    timestamp    not null default now(),
    updated_at    timestamp,
    book_id       bigint       not null,
    user_id       bigint       not null,

    constraint fk_borrow_book
        foreign key (book_id) references book (id),

    constraint fk_borrow_user
        foreign key (user_id) references library_user (id)
);

create index idx_borrow_book on borrow (book_id);
create index idx_borrow_user on borrow (user_id);

-- =========================
--  FINE
-- =========================
create table fine
(
    id            bigint not null
        constraint fine_pkey primary key,
    amount        numeric(19,2) not null,
    issued_date   timestamp     not null,
    paid          boolean       not null default false,
    paid_date     timestamp,
    created_at    timestamp     not null default now(),
    updated_at    timestamp,
    borrow_id     bigint        not null,

    constraint fk_fine_borrow
        foreign key (borrow_id) references borrow (id)
);

create index idx_fine_borrow on fine (borrow_id);
