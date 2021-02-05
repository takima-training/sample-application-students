FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y software-properties-common
RUN apt-add-repository --yes --update ppa:ansible/ansible
RUN apt-get install -y ansible

COPY sample-application-students/id_rsa /etc/id/
COPY sample-application-students/ansible /etc/ansible

RUN chmod 400 /etc/id/id_rsa
WORKDIR /etc/ansible