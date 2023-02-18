CREATE TABLE IF NOT EXISTS `oauth_access_token`
(
    `token_id`          varchar(255) NOT NULL,
    `token`             blob,
    `authentication_id` varchar(255) DEFAULT NULL,
    `user_name`         varchar(255) DEFAULT NULL,
    `client_id`         varchar(255) DEFAULT NULL,
    `authentication`    blob,
    `refresh_token`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`token_id`)
);

CREATE TABLE IF NOT EXISTS `oauth_refresh_token`
(
    `token_id`       varchar(255) NOT NULL,
    `token`          blob,
    `authentication` blob,
    PRIMARY KEY (`token_id`)
);

create table if not exists oauth_client_token
(
    token_id          VARCHAR(256),
    token             blob,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_approvals
(
    userId         VARCHAR(255) NOT NULL,
    clientId       VARCHAR(255) NOT NULL,
    scope          VARCHAR(255),
    status         VARCHAR(10)  NOT NULL,
    expiresAt      TIMESTAMP    NOT NULL,
    lastModifiedAt TIMESTAMP    NOT NULL,
    CONSTRAINT pk_oauth_approvals PRIMARY KEY (userId, clientId, scope)
);

