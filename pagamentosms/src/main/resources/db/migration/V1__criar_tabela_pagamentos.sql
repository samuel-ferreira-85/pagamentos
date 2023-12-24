CREATE TABLE pagamentos (
   id BIGINT(20) AUTO_INCREMENT NOT NULL,
   nome VARCHAR(100) DEFAULT NULL,
   valor DECIMAL(19,2) NOT NULL,
   numero VARCHAR(19) DEFAULT NULL,
   expiracao VARCHAR(7) NOT NULL,
   codigo VARCHAR(3) DEFAULT NULL,
   status VARCHAR(255) NOT NULL,
   pedido_id BIGINT(20) NOT NULL,
   forma_pagamento_id BIGINT(20) NOT NULL,
   CONSTRAINT pk_pagamentos PRIMARY KEY (id)
);