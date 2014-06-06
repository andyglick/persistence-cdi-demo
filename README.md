CDI Persistence Demo
====================

Much to my surprise the Spring Data JPA, and most probably the Spring Data Commons projects are actually configured 
such that Spring Data Repositories are compatible with CDI containers out of the box. In this project I am using the 
Apache DeltaSpike CDI implementation with the OpenWebBeans container and while there is a Spring configuration file 
associated with the project, it isn't being referenced. Via the Spring CDIRepositoryBean implementation the DeltaSpike 
scanner finds the Spring Repository classes and configures them and using the repository classes I have been able to 
write to the database. 
 
The problem, such as it is, is that I haven't worked out how to configure a CDI compliant Transaction Manager, so the 
inserts are getting lost. That isn't nearly as bad as it sounds. 
    
