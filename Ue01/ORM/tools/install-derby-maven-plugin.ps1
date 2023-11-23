# Enable execution of Powershell scripts before executing this script: 
#    Set-ExecutionPolicy Unrestricted -Scope CurrentUser

$ScriptFolder = Split-Path $MyInvocation.MyCommand.Path

& mvn `
		"install:install-file" `
		"-Dfile=$(Join-Path $ScriptFolder 'derby-maven-plugin-1.4.jar')" `
		"-DgroupId=org.jheinzel.maven" `
		"-DartifactId=derby-maven-plugin" `
		"-Dversion=1.4" `
		"-Dpackaging=maven-plugin" `
		"-DpomFile=$(Join-Path $ScriptFolder 'derby-maven-plugin-1.4.pom')"
