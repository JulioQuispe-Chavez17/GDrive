volume:
	kubectl apply -f PersistentVolume.yaml
volumeClaim:
	kubectl apply -f PersistentVolumeClaim.yaml
credential:
	kubectl apply -f Credential.yaml
deploy:
	kubectl apply -f Deployment.yaml
service:
	kubectl apply -f Service.yaml
all:
	kubectl apply -f .
delete:
	kubectl delete -f .

.PHONY: volume volumeClaim credential deploy service