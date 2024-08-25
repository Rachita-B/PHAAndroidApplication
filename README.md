#  Android PHA(Potentially Harmul Applications) Android Malware Simulation & Maskware App

# Servers:
Login Server: http://example-login-server.com
Data Server: http://example-data-server.com
SMS Server: http://example-sms-server.com
Note: The servers mentioned here are placeholders. You can replace them with actual server URLs relevant to your setup.

# Description:
This project demonstrates various malware behaviors within a single Android application, serving as an educational tool for understanding and analyzing spyware activities. The app simulates several malicious actions commonly associated with Android malware, including:

# 1.Hostile Downloader & Phishing:

Downloads an APK file in the background and prompts the user to install it after switching from Wi-Fi to mobile data.
Loads URLs associated with phishing or malicious sites, like betting sites, after certain user interactions.

# 2.Call & SMS Fraud:

Sends promotional messages to contacts retrieved from a server, as well as to all saved contacts and predefined premium numbers, using Volley and Retrofit for network operations.
Initiates a phone call after user sign-out, leveraging permissions granted at the app's start. The call is immediately terminated, prompting the user to switch to mobile data, after which the app redirects back and loads a malicious URL.

# 3.Network Switching:

Changes the device's connectivity from Wi-Fi to mobile data, requiring user permission due to API 30 security restrictions. The app automatically disables Wi-Fi when the user clicks a button, but the mobile data switch must be done manually by the user.
Simulated Login Flow:

Presents a fake Instagram login page, where credentials are checked against a server via Retrofit. If login is successful, the user’s profile is displayed, and data is sent to a specified server (e.g., http://example-login-server.com).
Regardless of login success, credentials are sent to the same server, and the app behaves similarly after the user signs out.

# 4.Gift Claim Button:

Clicking this button triggers an automatic APK download in the background, followed by an installation prompt once the download is complete.
Maskware Functionality:

Pretends to be another legitimate application when loading URLs, deceiving the user into believing they are interacting with a different app.
Loads different URLs based on specific times and dates, such as loading a new URL after September 22nd or at specific times, simulating targeted malicious activities.
# 5.Maskware Actions:

During certain operations, the app disguises itself as another legitimate application, misleading the user while performing malicious activities in the background.
# Network Switching & URL Loading:

Once mobile data is enabled, the app redirects back to its main interface, where it automatically loads a URL based on the time and date. For example, after September 22nd, a different URL is loaded compared to earlier dates.
# Gift Claim Button:

The app features a "Claim Gift" button, which, when clicked, triggers a background APK download. Once the download is complete, an installation prompt is shown to the user.

# Flow of the Application:
# Initial Setup:

Upon launching the app, the user is prompted to grant various permissions, including those for making calls, sending SMS, and switching network connections.
Login Simulation:

The user is presented with a fake Instagram login page. Credentials entered are sent to a specified server (http://example-login-server.com) using Retrofit. If the login is deemed successful, the app displays a profile page; otherwise, it simulates a failed login but still sends the data to the server.
Post-Login Activities:

After the user signs out, the app initiates a phone call using the previously granted permissions. The call is terminated immediately by the user, and the app prompts the user to enable mobile data to continue.
After mobile data enabled it will load a url pretending to be another application this url will be loaded differently as per date and time.
A Button will be shown to claim gift user click on it and malicious apk file will get downloaded and installed in background.

# Building the APK:
If you'd like to experiment with reverse engineering, you can build the APK from Android Studio by following these steps:

# Clone the Repository:

bash
Copy code
git clone https://github.com/your-repo-url.git

Open the Project in Android Studio:

Launch Android Studio and select "Open an existing project."
Navigate to the cloned repository and select it.
 
 # Build the APK:

Go to Build in the top menu and select Build Bundle(s)/APK(s) → Build APK(s).
The APK file will be generated in the app/build/outputs/apk/ directory.

# If want to do reverse engineering for this application 

# Reverse Engineering Tools:
You can use the following tools to analyze and understand the APK:

# 1.Jadx:

A decompiler that allows you to convert APK files into readable Java source code.
Usage: jadx -d out/ app.apk

# 2.Apktool:

A tool for reverse engineering Android apps. It can decode resources to nearly original form and rebuild them after making modifications.
Usage: apktool d app.apk

# 3.APK Framework Detector:

A tool used to identify the framework or language used to build the APK, such as Java, Kotlin, or others.
Usage: Run the tool with the APK file to get insights into the underlying framework.
These tools help in understanding how the app was built and identifying potential vulnerabilities or the programming language used in its development.


# Screenshots:
Login Screen: A screenshot showing the fake Instagram login page.
Permissions Request: Screenshot showing the permission requests made at the app’s startup.
Network Switch: Image demonstrating the transition from Wi-Fi to mobile data.
Phishing URL: A screenshot of the browser loading a phishing or malicious URL.
Gift Claim Feature: Image showing the button for claiming a gift, leading to an APK download.
Maskware in Action: A screenshot showing how the app pretends to be another application while loading a URL.

# Usage Note:

# This project is intended for educational purposes only, showcasing the potential dangers of certain malware behaviors in a controlled environment.
