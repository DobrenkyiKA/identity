### Generate keys:
openssl genrsa -out private.pem 2048

openssl rsa -in private.pem -pubout -out public.pem


put them in:

src/main/resources/keys/

├── private.pem

└── public.pem