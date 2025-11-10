#!/usr/bin/env groovy
import com.urbancode.air.plugin.helpers.NewAirPluginTool

/*
* Developer: Vikrant Singh
* Email: virkantsnirban@hotmail.com
* Email: vikrantsnirban@irissoftware.com
*/

def apTool = new NewAirPluginTool(this.args[0], this.args[1]);
def props = apTool.getStepProperties();

def jobName = props['jobName'];
def jenkinsURL = props['jenkinsURL'];
def userName = props['userName'];
def userPassword = props['userPassword'];
def action = props['action'];
boolean checkSCMChanges = props['checkSCMChanges'];
boolean waitUntilStart = props['waitUntilStart'];
boolean WaitUntilCompletion = props['WaitUntilCompletion'];
boolean printJobOutput = props['printJobOutput'];

println ' Data: action: ' + action
println ' Data: jobName: ' + jobName
println ' Data: jenkinsURL: ' + jenkinsURL
println ' Data: userName: ' + userName
println ' Data: userPassword: ' + userPassword
println ' Data: checkSCMChanges: ' + checkSCMChanges
println ' Data: waitUntilStart: ' + waitUntilStart
println ' Data: WaitUntilCompletion: ' + WaitUntilCompletion
println ' Data: printJobOutput: ' + printJobOutput

switch (action){
	case "start":
		startJob(jenkinsURL,userName,userPassword,jobName,checkSCMChanges,waitUntilStart,WaitUntilCompletion,printJobOutput)
		break
	case "enable":
		enableJob(jenkinsURL,userName,userPassword,jobName)
		break
	case "disable":
		disableJob(jenkinsURL,userName,userPassword,jobName)
		break
	case "delete":
		deletJob(jenkinsURL,userName,userPassword,jobName)
		break
}


void startJob(String jenkinsURL,String userName,String userPassword,String jobName, boolean checkSCMChanges, boolean waitUntilStart, boolean WaitUntilCompletion, boolean printJobOutput){
	String[] params = null

		params = ["-s",jenkinsURL,"build",jobName]
	if (checkSCMChanges){
		params = ["-s",jenkinsURL,"build",jobName,"-c","--username",userName,"--password",userPassword]
		if(userName != ""){
			params = ["-s",jenkinsURL,"build",jobName,"-c","--username",userName,"--password",userPassword]
		}
	}
	if (waitUntilStart){
		params = ["-s",jenkinsURL,"build",jobName,"-w","--username",userName,"--password",userPassword]
	}
	if (WaitUntilCompletion){
		params = ["-s",jenkinsURL,"build",jobName,"-f","--username",userName,"--password",userPassword]
	}
	if (printJobOutput){
		params = ["-s",jenkinsURL,"build",jobName,"-f","-v","--username",userName,"--password",userPassword]
	}
    println "[Action] Running Command: java -jar <jenkins-cli.jar> " + params.join(" ")
	CLI._main(params)
	println("Job started successfully.")
}

void enableJob(String jenkinsURL,String userName,String userPassword,String jobName){
	String[] params = ["-s",jenkinsURL,"enable-job",jobName,"--username",userName,"--password",userPassword]
    println "[Action] Running Command: java -jar <jenkins-cli.jar> " + params.join(" ")
	CLI._main(params)
	println("Job enabled successfully.")
}

void disableJob(String jenkinsURL,String userName,String userPassword,String jobName){
	String[] params = ["-s",jenkinsURL,"disable-job",jobName,"--username",userName,"--password",userPassword]
    println "[Action] Running Command: java -jar <jenkins-cli.jar> " + params.join(" ")
	CLI._main(params)
	println("Job disabled successfully.")
}

void deletJob(String jenkinsURL,String userName,String userPassword,String jobName){
	String[] params = ["-s",jenkinsURL,"delete-job",jobName,"--username",userName,"--password",userPassword]
    println "[Action] Running Command: java -jar <jenkins-cli.jar> " + params.join(" ")
	CLI._main(params)
	println("Job deleted successfully.")
}
