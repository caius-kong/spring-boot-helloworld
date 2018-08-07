worker_processes  1;
events {
    worker_connections  1024;
}
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;
    proxy_send_timeout 60;
    proxy_read_timeout 1800s;
    client_max_body_size 300M ;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    '$status $body_bytes_sent "$http_referer" '
    '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  logs/access.log  main;

    include ./conf.d/*.conf;

    <#list http.servers as server>
    server{
        listen ${server.listen_port}<#if server.server_name=="_"> default_server</#if>;
        server_name ${server.server_name};
        <#list server.locations as location>
        location ~* ^${location.path}\/(?<baseuri>.*) {
            <#if location.proxy_pass=="">
            proxy_pass http://www.baidu.com;
            <#else>
            proxy_pass http://${location.proxy_pass};
            </#if>
        }
        </#list>
    }
    </#list >
}
