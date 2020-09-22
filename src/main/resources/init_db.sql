CREATE SCHEMA `my_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `my_shop`.`products` (
                                      `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                      `product_name` VARCHAR(225) NOT NULL,
                                      `product_price` DOUBLE NOT NULL,
                                      `deleted` BOOLEAN NOT NULL DEFAULT false,
                                      PRIMARY KEY (`product_id`));

CREATE TABLE `my_shop`.`users` (
                                   `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                   `user_name` VARCHAR(225) NOT NULL,
                                   `user_login` VARCHAR(225) NOT NULL,
                                   `user_password` VARCHAR(225) NOT NULL,
                                   `deleted` BOOLEAN NOT NULL DEFAULT false,
                                   PRIMARY KEY (`user_id`));

CREATE TABLE `my_shop`.`orders` (
                                    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                    `user_id` BIGINT(11) NOT NULL,
                                    `deleted` BOOLEAN NOT NULL DEFAULT false,
                                    PRIMARY KEY (`order_id`),
                                    INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
                                    CONSTRAINT `orders_users_fk`
                                        FOREIGN KEY (`user_id`)
                                            REFERENCES `my_shop`.`users` (`user_id`)
                                            ON DELETE NO ACTION
                                            ON UPDATE NO ACTION);

CREATE TABLE `my_shop`.`roles` (
                                   `role_id` BIGINT(11) NOT NULL,
                                   `role_name` VARCHAR(255) NOT NULL,
                                   PRIMARY KEY (`role_id`));


CREATE TABLE `my_shop`.`shopping_carts` (
                                            `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                            `user_id` BIGINT(11) NOT NULL,
                                            `deleted` BOOLEAN NOT NULL DEFAULT false,
                                            PRIMARY KEY (`cart_id`),
                                            INDEX `carts_users_fk_idx` (`user_id` ASC) VISIBLE,
                                            CONSTRAINT `carts_users_fk`
                                                FOREIGN KEY (`user_id`)
                                                    REFERENCES `my_shop`.`users` (`user_id`)
                                                    ON DELETE NO ACTION
                                                    ON UPDATE NO ACTION);


CREATE TABLE `my_shop`.`orders_products` (
                                             `order_id` BIGINT(11) NOT NULL,
                                             `product_id` BIGINT(11) NOT NULL,
                                             INDEX `orders_orders_fk_idx` (`order_id` ASC) VISIBLE,
                                             INDEX `products_products_fk_idx` (`product_id` ASC) VISIBLE,
                                             CONSTRAINT `orders_products_orders_fk`
                                                 FOREIGN KEY (`order_id`)
                                                     REFERENCES `my_shop`.`orders` (`order_id`)
                                                     ON DELETE NO ACTION
                                                     ON UPDATE NO ACTION,
                                             CONSTRAINT `orders_products_products_fk`
                                                 FOREIGN KEY (`product_id`)
                                                     REFERENCES `my_shop`.`products` (`product_id`)
                                                     ON DELETE NO ACTION
                                                     ON UPDATE NO ACTION);


CREATE TABLE `my_shop`.`shopping_carts_products` (
                                                     `cart_id` BIGINT(11) NOT NULL,
                                                     `product_id` BIGINT(11) NOT NULL,
                                                     INDEX `carts_products_products_fk_idx` (`product_id` ASC) VISIBLE,
                                                     INDEX `carts_products_carts_fk_idx` (`cart_id` ASC) VISIBLE,
                                                     CONSTRAINT `carts_products_products_fk`
                                                         FOREIGN KEY (`product_id`)
                                                             REFERENCES `my_shop`.`products` (`product_id`)
                                                             ON DELETE NO ACTION
                                                             ON UPDATE NO ACTION,
                                                     CONSTRAINT `carts_products_carts_fk`
                                                         FOREIGN KEY (`cart_id`)
                                                             REFERENCES `my_shop`.`shopping_carts` (`cart_id`)
                                                             ON DELETE NO ACTION
                                                             ON UPDATE NO ACTION);


CREATE TABLE `my_shop`.`users_roles` (
                                         `user_id` BIGINT(11) NOT NULL,
                                         `role_id` BIGINT(11) NOT NULL,
                                         INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
                                         INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
                                         CONSTRAINT `users_roles_users_fk`
                                             FOREIGN KEY (`user_id`)
                                                 REFERENCES `my_shop`.`users` (`user_id`)
                                                 ON DELETE NO ACTION
                                                 ON UPDATE NO ACTION,
                                         CONSTRAINT `users_roles_roles_fk`
                                             FOREIGN KEY (`role_id`)
                                                 REFERENCES `my_shop`.`roles` (`role_id`)
                                                 ON DELETE NO ACTION
                                                 ON UPDATE NO ACTION);
