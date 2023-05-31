
# Pod

pod:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-cashier

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-cashier -- /bin/bash

pod-logs:
	kubectl logs -f spring-cashier

# Deployment

deployment:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-cashier

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-cashier-deployment

# Service

service:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-cashier-service -o wide

service-delete:
	kubectl delete service spring-cashier-service

# Ingress

ingress:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-cashier-ingress




