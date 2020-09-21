CREATE SCHEMA `my_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `my_shop`.`products` (
    `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `product_name` VARCHAR(225) NOT NULL,
    `product_price` DOUBLE NOT NULL,
    `deleted` BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (`product_id`));

