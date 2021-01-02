const { Client, logger, Variables, BasicAuthInterceptor } = require("camunda-external-task-client-js");


const basicAuth = new BasicAuthInterceptor({
  username: "demo",
  password: "demo"
});
// configuration for the Client:
//  - 'baseUrl': url to the Workflow Engine
//  - 'logger': utility to automatically log important events
const config = { baseUrl: "http://localhost:8080/engine-rest", use: logger, interval: 5000, asyncResponseTimeout: 3000, maxTasks: 1, workerId: "ServiceNowConnector", interceptors: [basicAuth] };

// create a Client instance with custom configuration
const client = new Client(config);

const sleep = (waitTimeInMs) => new Promise(resolve => setTimeout(resolve, waitTimeInMs));


// susbscribe to the topic: 'creditScoreChecker'
client.subscribe("camunda.onboarding.procureLaptop", async function({ task, taskService }) {

    const processVariables = new Variables();
    console.log("Sent Laptop request to ServiceNow, waiting for fulfillment now...");
    await sleep(10000);
    console.log("Laptop is prepared now...");

    await taskService.complete(task, processVariables);
});
