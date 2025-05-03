# Clone o repositório

https://github.com/paulaleticia21/Crud-Consulta.git

# Entre na pasta
cd consulta-medica/src

# Compile o projeto
mvn clean compile

# Defina as variáveis de ambiente 
AWS_ACCESS_KEY_ID="test"
AWS_SECRET_ACCESS_KEY="test"

# Suba o Docker
docker-compose up

# Acesse o container
exec do localstack

# Crie a tabela no LocalStack
aws configure:

- AWS Access Key ID [None]: test 
- AWS Secret Access Key [None]: test 
- Default region name [None]: us-east-1 
- Default output format [None]: json

# Executar o comando para criar a tabela no localstack:
aws --endpoint-url=http://localhost:4566 dynamodb create-table --table-name Consultations --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

# Liste as tabelas:
aws --endpoint-url=http://localhost:4566 dynamodb list-tables

# Execute o projeto:
mvn spring-boot:run

# Testando a API com Insomnia ou cURL

# POST:
curl -X POST http://localhost:8080/consultations \
-H "Content-Type: application/json" \
-d '{"id":"1", "patientName":"Paula Leticia Braz", "doctorName":"Dr. Smith"}'

# Get List
curl -X GET http://localhost:8080/consultations

# Get List por id
curl -X GET http://localhost:8080/consultations/1

# Delete consulta
curl -X DELETE http://localhost:8080/consultations/1
