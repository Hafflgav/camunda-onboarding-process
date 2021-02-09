# Camunda RPA Bridge 
The Camunda RPA bridge is a standalone application that allows to call RPA (robotic process automation) bots from BPMN models deployed to a Camunda engine.
RPA bots can be orchestrated as External Tasks using the Camunda Modeler and Cawemo.

The Camunda RPA bridge serves as a connector between Camunda (BPMN) on the one side and UiPath (RPA) on the other side. Processes running inside the Camunda engine can define external tasks that are marked as RPA tasks.

The bridge extends the regular Java External Task Client, fetches and locks the RPA tasks and starts a job in UiPath. 
Once the job is done, UiPath will notify a webhook in the bridge about the job result and state (Success/Failure). 
Alternatively, a polling mechanism requesting status updates from UiPath can be configured. Either way, the bridge will complete the previously locked external task and pass any result variables received from UiPath along to the Camunda engine.

The application.yml is used for the configuration of the bridge. For some more insights take a look there. 

For more information about the RPA Bridge itself check-out:
https://docs.camunda.org/manual/latest/user-guide/camunda-bpm-rpa-bridge/ 