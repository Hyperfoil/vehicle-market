#!/usr/bin/env bash
sudo groupadd docker && sudo gpasswd -a ${USER} docker && sudo systemctl restart docker
newgrp docker
sudo grubby --update-kernel=ALL --args="systemd.unified_cgroup_hierarchy=0"
sudo usermod -aG docker $USER
sudo systemctl daemon-reload && sudo systemctl restart docker