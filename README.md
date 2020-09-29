#防火墙启动和关闭（重启后失效）
service iptables start/stop
#防火墙启动和关闭（重启后永久性生效）
chkconfig iptables on/off
#linux直接进入mysql命令
mysql -uroot -p
#mysql启动/关闭
service mysql start/stop
#nacos启动(单个启动)
./startup.sh -m standalone
#关闭nacos
./shutdown.sh
#查看有几个nacos启动
ps -ef|grep nacos|grep -v grep | wc -l
#es启动 先切到es用户
./elasticsearch -d
#查看端口号是否被占用
netstat -aon|findstr "8081"
#查看pid进程
tasklist|findstr "9088"
#redis启动
/usr/local/redis/src/redis-server /usr/local/redis/redis.conf
#redis关闭
./redis-cli -a 123456  shutdown
#alibaba-sentiel
java -Dserver.port=8818 -Dcsp.sentinel.dashboard.server=localhost:8818 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.6.2.jar