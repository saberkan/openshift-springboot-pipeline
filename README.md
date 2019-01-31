# openshift-springboot-pipeline
Demo of pipeline deployment into OKD

# Presentation
This project shows how to deploy a springboot application into OKD from scratch, and how to promote the project to production environment

# Pre-requisite
- Install oc cli, to be found in the following link : https://github.com/openshift/origin/releases/tag/v3.10.0

# How to
## Step 1 : Install a local running OKD with oc cluster up
<pre>
$ oc cluster up --skip-registry-check=true
# Wait until the cluster is up and connect to the cluster 
$ oc login -u system:admin
</pre>

## Step 2 : Init a jenkins in pipeline environment
When running a BuildConfiguration with Jenkins strategy in OKD cluster, it popup a running jenkins master in the same namespace. This jenkins is alredy configured with kubernetes plugin that uses slaves on openshift to run the jobs.

Let's start by running creating a namespace, and popup a master jenkins on it using a BuildConfiguration
<pre>
$ cd resources/
$ oc new-project pipeline-saberkan
$ oc apply -f pipeline_bc_init.yaml
</pre>

Remarque : saberkan is my personal username, you can edit with your own

Now wait few minutes until jenkins is fully running. if the deployment can't pull the image from the local registry, donc forget to enable the access to the regitry by setting --insecure-registries in docker configuration.

TODO :
## Step 3 : Edit the pipeline
## Step 4 : Create developement, staging & production environments
## Step 5 : Launch the pipeline
