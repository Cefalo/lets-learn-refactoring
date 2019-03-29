## IMPROVEMENTS ##

There can be many improvements of the solution to current problem we have refactored so far. 
Even you can completely rewrite the application to make the solution more flexible, reusable and understandable. 

But here are few improvements you may do from the current state of the refactored codebase -

1. **Custom Error Handling** - For example, rather than throwing **JSchException**()comes from **jsch** library we have used here) we can write our own Custom Exception(e.g SSHClientException). This exception may wrap the other Exceptions and also can encapsulate useful features to handle application errors.
2. **CLI Response as Object** - Ok, so far we are taking CLI response as String from **String execute(String command) throws JSchException, IOException** method. This method accumulates different pieces of responses and return. Obviously it is not best approach because we can bind different parts of the response into an Object like Response which may be **command**, **output** and **status**. This will give the the UI designers much flexibility to design the UI as they wanted. Not only that binding response to an object will help us to write better Unit Tests.
3. **Using Third Party LIB** - This is optional but sometimes using third party library may help organizing your code. For example in this application we have written boilerplate code to handle IO/Network operations. We could have used library like apache commons-io library. There are lot more libraries you will find in this open source world.
4. **Accept Server Configs in different format** - In this application we have accepted server config in JSON format only(e.g app1.json). Let the application accept different file formats like .xml, .properties, .yaml or even .txt. And obviously we will be doing it in Object oriented way. 

Feel free to add more if you find the areas of improvements. 