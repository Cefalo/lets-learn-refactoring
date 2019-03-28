Suppose you are an application developer currently working for Amazon. Amazon wants you to build a SSH Client tool which will be used by their customers who purchase various servers to make their application  up & running on Amazon cloud.

Let's consider one of the Amazon's customer has the following server setup -

1. Three app servers(app1, app2 and app3)
2. Two Database servers(db1 and db2)
3. One file server to keep the binaries(images, video etc.)

After subscribing to AWS cloud Amazon will be providing the servers info and their respective credentials to connect and do server administration works.

In the application all the server infos will be stored as json config file. Your application will try to read the local configs first and if there is no local config the tool will be reading server details from remote source(over the network). After reading the remote server details the local config will also be updated for later use.

Make sure your tool can communicate from different OS(OSX, Unix, Window etc.)

Amazon may change their config file format from json to other(xml, yaml, properties etc.)

Remember, your stakeholders won't mind to expect something like following from your system-

1. Flexibility
2. Extensibility
3. Reusability
4. Maintainability 
5. Understandability

And lastly make sure you understand all the requirements from your stakeholders. 
