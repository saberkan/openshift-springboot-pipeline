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

## Step 2 : Create pipeline, developement & staging
<pre>
$ oc new-project development-saberkan
$ oc new-project staging-saberkan
$ oc new-project pipeline-saberkan
</pre>

# Add role to developer and jenkins user
<pre>
$ oc adm policy add-role-to-user admin developer -n development-saberkan
$ oc adm policy add-role-to-user admin developer -n staging-saberkan
$ oc adm policy add-role-to-user admin developer -n pipeline-saberkan
$ oc adm policy add-role-to-user admin developer -n openshift
$ oc adm policy add-role-to-user admin system:serviceaccount:pipeline-saberkan:jenkins -n development-saberkan
$ oc adm policy add-role-to-user admin system:serviceaccount:pipeline-saberkan:jenkins -n staging-saberkan
$ oc adm policy add-role-to-user admin system:serviceaccount:pipeline-saberkan:jenkins -n openshift
$ oc adm policy add-role-to-user admin system:serviceaccount:development-saberkan:builder -n openshift
</pre>

## Step 3 : Init a jenkins in pipeline environment
When running a BuildConfiguration with Jenkins strategy in OKD cluster, it popup a running jenkins master in the same namespace. This jenkins is alredy configured with kubernetes plugin that uses slaves on openshift to run the jobs.

Let's start by running creating a namespace, and popup a master jenkins on it using a BuildConfiguration
<pre>
$ cd resources/
$ oc apply -f pipeline_bc_init.yaml -n pipeline-saberkan
</pre>

Remarque : saberkan is my personal username, you can edit with your own

Now wait few minutes until jenkins is fully running. if the deployment can't pull the image from the local registry, donc forget to enable the access to the regitry by setting --insecure-registries in docker configuration.

## Step 4 : Import S2I image to build springboot project
<pre>
$ docker pull appuio/s2i-maven-java 
$ oc import-image s2i-maven-java:latest --from=appuio/s2i-maven-java --confirm -n openshift
</pre>

## Step 5 : Create objects and configs in namespaces
<pre>
$ cd ../templates
$ oc process -f objects/development/objects-development.yaml | oc create -f - -n development-saberkan
$ oc process -f objects/staging/objects-staging.yaml | oc create -f - -n staging-saberkan
$ oc create cm springboot-cm --from-file=configMaps/development/ -n development-saberkan
$ oc create cm springboot-cm --from-file=configMaps/staging/ -n staging-saberkan
$ oc create is springboot -n openshift
</pre>

## Step 6 : Launch the pipeline
Go to : https://127.0.0.1:8443/console/project/pipeline-saberkan/browse/pipelines/springboot-saberkan-pipeline?tab=history
and run pipeline
