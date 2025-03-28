
## Installation

Installation PostgreSQL

```bash
  sudo apt update

  sudo apt install -y postgresql

  systemctl status postgresql
```
New user

```bash
sudo -iu postgres createuser -P --interactive fernando
```
View:
```bash
Enter password for new role: *******
Enter it again: *******
Shall the new role be a superuser? (y/n) y
```

New database
```bash
createdb fernando -O fernando
```

Conexion

```bash
psql -U fernando -d postgres
```

External Conexion
```bash
psql -U fernando -d fernando -h localhost -p 5432
```

Installation pgAdmin

Install the public key for the repository (if not done previously):

```bash
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | sudo gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg
```

Create the repository configuration file:

```bash
sudo sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list && apt update'
```
Install for both desktop and web modes:
```bash
sudo apt install pgadmin4
```
Install for desktop mode only:
```bash
sudo apt install pgadmin4-desktop
```
Install for web mode only: 
```bash
sudo apt install pgadmin4-web 
```
Configure the webserver, if you installed pgadmin4-web:
```bash
sudo /usr/pgadmin4/bin/setup-web.sh
```