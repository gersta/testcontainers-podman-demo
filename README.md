# Testcontainers Podman Demo
The goal of this project is twofolded:
- First, I want to write my first tests ever with Testcontainers and start to learn more about it
- Second, I want to learn about Podman and other container technologies besides Docker to boost my daily coding life

## Local Database Setup
In order to test something with Testcontainers, we will need an actual application setup first.

I will use a Postgre database instance running via Podman, which exposes the connection on `localhost:5432`:

```
podman run \
-p 5432:5432 \
-e POSTGRES_PASSWORD='<PASSWORD>' \
--name postgres postgres
```

And for any consecutive runs, just restart the container:

```
podman restart postgres
```

In order to connect Spring boot with the database, you need to set the following two
properties as environment variables:

```
SPRING_DATASOURCE_USERNAME=<USERNAME>
SPRING_DATASOURCE_PASSWORD=<PASSWORD>
```

The default username of postgres is `postgres` and the password is the one you set in the `podman run`
command above. Further, postgres's default database name is also `postgres`, which is preset in the `application.properties`
file.

## Getting Testcontainers to run
First, we will need a podman VM that will run our containers later

`podman machine init`

Now, we can start the VM:

`podman machine start`

And check its connectivity via, which could like follows:

`podman system connection list`

```
% podman system connection list
Name                         Identity                                           URI
podman-machine-default*      /Users/<USER>/.ssh/podman-machine-default  ssh://core@localhost:53136/run/user/1000/podman/podman.sock
podman-machine-default-root  /Users/<USER>/.ssh/podman-machine-default  ssh://root@localhost:53136/run/podman/podman.sock
```

This will give us the **port** and the **path to the podman.sock file**.

Now, we can expose the podman.sock to the outside world (the host machine) and thus make it available to Testcontainers:

```
ssh -i \
~/.ssh/podman-machine-default \
-N core@localhost \
-p 53136 \
-L'/tmp/podman.sock:/run/user/1000/podman/podman.sock'
```

The ssh options are as follows:

```
- i: allows us to specify an identity file to use for the connection
- p: specify the port to establish the SSL connection on. Note: The VM does not expose SSH on 22 as usually done
- N: do not execute anything. Keeps the terminal window open
- L: Forward connections from the local socket to the remote socket
```

Especially `-L` is important in order to get access into the VM based on a file, which can then be referenced as follows (or whatever is your OS's approach to export environment variables) on the host machine
where we want to run Testcontainers:

```
export DOCKER_HOST=unix:///temp/podman.sock
```

You will also need to set other environment variables to circumvent errors:

As described in the [GitHub issue about connection to Ryuk](https://github.com/testcontainers/testcontainers-java/issues/3609), you will need to disable it (with all consequences):
```
export TESTCONTAINERS_RYUK_DISABLED=true
```

## Troubleshooting

### Address already in use
When you try to setup the SSH tunnel to the VM, you get the the following error:

`ssh -i ~/.ssh/podman-machine-default core@localhost -p 53136 -L'/tmp/podman.sock:/run/user/1000/podman/podman.sock'`

```
unix_listener: cannot bind to path /tmp/podman.sock: Address already in use
Could not request local forwarding.
Fedora CoreOS 35.20220213.2.0
Tracker: https://github.com/coreos/fedora-coreos-tracker
Discuss: https://discussion.fedoraproject.org/tag/coreos

Last login: Fri Feb 25 14:51:28 2022 from 192.168.127.1
[core@localhost ~]$
```

Although you are ssh'd into the VM afterwards, the tunnel setup failed.

You can jump out of the VM and then delete the local `.sock`:

1. Jump out of the VM:

`[core@localhost ~]$ exit`

2. Delete the `.sock` file locally:

`rm /tmp/podman.sock`