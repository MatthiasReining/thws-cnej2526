# Students on Hetzner Cloud

http://hetzner.com/

Powerful ideas deserve powerful servers

Deploy as Docker container

## Buy Hetzer Sever

Buy CX23 server (VM)

Install DOCKER App image (based on Ubunto)

## Docker

### Create Docker Registry

- Create _AWS ECR_ (Elatic Container Registry) for `thws/students`
- Use the code snippets for manage credentials

Push local docker image (`thws/students`)to AWS ECR

Follow "View Push Commands" on ECR Repositories / Images screen

Double check the tags for your commands, here in a nutshell:

- `aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 908027413209.dkr.ecr.eu-central-1.amazonaws.com`
  (you have to use your account id)
- `docker build -f src/main/docker/Dockerfile.native -t test-03/students .`
  Build your image with the Quarkus native image
- `docker tag test-03/students:latest 908027413209.dkr.ecr.eu-central-1.amazonaws.com/test-03/students:latest`
  Tag for AWS (use your account id)
- `docker push 908027413209.dkr.ecr.eu-central-1.amazonaws.com/test-03/students:latest`
  Push to AWS (use your account id)

### Setup Hetzner Server

- install aws cli  
  https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html  
  `snap install aws-cli --classic`

- run `aws configure`

you need a AWC access key:

https://us-east-1.console.aws.amazon.com/iam/home?region=eu-central-1#/security_credentials

AWS Access Key ID [None]: AKIA5G2VGYLMZXLOFKFR
AWS Secret Access Key [None]: xxxx
Default region name [None]: eu-central-1
Default output format [None]:

Afterwards the login as shown in "View Push Commands" can be used

`aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 908027413209.dkr.ecr.eu-central-1.amazonaws.com`

### RUN

    docker run -i --rm -p 8080:8080 908027413209.dkr.ecr.eu-central-1.amazonaws.com/test-03/students

## Setup NGINX (API Gateway)

# Install and setup NGINX

    apt-get install nginx
    systemctl enable --now nginx

    nano /etc/nginx/sites-available/thws-app

```
server {
    listen 80;
    server_name thws.hill91labs.com;  # change to your domain or IP

    # Optional: allow larger uploads
    client_max_body_size 50m;

    location / {
        proxy_pass http://127.0.0.1:8080/;

        # Preserve original host/client info
        proxy_set_header Host              $host;
        proxy_set_header X-Real-IP         $remote_addr;
        proxy_set_header X-Forwarded-For   $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        # Good defaults
        proxy_http_version 1.1;
        proxy_read_timeout 60s;
        proxy_connect_timeout 5s;
    }
}

```

    ln -s /etc/nginx/sites-available/thws-app /etc/nginx/sites-enabled/
    nginx -t
    systemctl reload nginx

# Install SSL certificate (let's encrypt)

https://certbot.eff.org/

    snap install --classic certbot
    ln -s /snap/bin/certbot /usr/bin/certbot
    certbot --nginx
