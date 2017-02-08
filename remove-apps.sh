#!/bin/bash
sp="/-\|"
sc=0
spin() {
   printf "\b${sp:sc++:1}"
   ((sc==${#sp})) && sc=0
}
endspin() {
   printf "\r"
}

echo "Uninstalling IOT_LAUNCHER category apps"

spin

userInstalledPackages=( $(adb shell pm list packages -3 | sed -e "s/^"package:"//" ) )

iotPackages=()
for userPackage in "${userInstalledPackages[@]}"
do
	:
	spin
	categoryLauncherText=$( adb shell pm dump $userPackage | grep "android.intent.category.IOT_LAUNCHER")
	if [ -n "${categoryLauncherText}" ]; then
		iotPackages+=(${userPackage})
	fi	
done
endspin

for iotPackage in "${iotPackages[@]}"
do
	:
	echo "Uninstalling $iotPackage"
	adb uninstall $iotPackage
done

echo "Complete"