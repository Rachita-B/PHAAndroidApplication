#  Android PHA(Potentially Harmul Applications) Android Malware Simulation & Maskware App
***********************************Code in master branch*********************************
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
git clone https://github.com/Rachita-B/PHAAndroidApplication.git

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


# Servers:

Login Server: https://dummyjson.com/users/auth/login 

For login credentials: https://dummyjson.com/users

Sending credentials data to Server: https://enlj46n3j0l2s.x.pipedream.net/ 

For verifying the response we are getting https://public.requestbin.com/r/enlj46n3j0l2s 

Can generate new endpoint at requestbin.com  https://public.requestbin.com/r

Getting Contacts from Server for sending SMS: https://jsonplaceholder.typicode.com/users


# Screenshots:


Permissions Request: Screenshot showing the permission requests made at the app’s startup.

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/ce1d5213-66dc-421d-87ff-f6fb8f336152">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/05736edb-f38f-4e0f-bef8-6ecea527d042">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/8d75241e-807c-4817-9b66-078111dcbac9">

Login Screen: A screenshot showing the fake Instagram login page.

<img width="360" height="650" alt="Picture1" src="https://github.com/user-attachments/assets/e07a3e01-8b1c-434c-b291-de82dbbfe3ed">

Login Succesful

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/598d2054-6f83-4172-a719-39236d84c7fa">

After Signout 

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/a730f83a-93c2-42a4-90cd-bf5e117425ba">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/968be89e-71f2-444f-8b46-a5cca6284033">

Network Switch: Image demonstrating the transition from Wi-Fi to mobile data.

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/ac84cc45-0e3f-4b1c-8769-dbc4928a2d4c">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/bbe1c8fe-99d2-40ce-8ad9-916dbb210f8e">


Phishing URL: A screenshot of the browser loading a phishing or malicious URL.

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/d9345bd9-bbc7-41e7-ac4b-f8a6b539d10a">

Gift Claim Feature: Image showing the button for claiming a gift, leading to an APK download.

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/311a8c1b-beb7-49f6-8b65-cf9d6e9b9ce7">


Maskware in Action: A screenshot showing how the app pretends to be another application while loading a URL.

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/4b25e681-567e-4d08-84f6-60cf3b03624b">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/1e516fb8-9172-4f81-8e42-02e620e781ab">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/abb279ea-cde4-41e0-9695-6adf310a709b">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/d37f3ee4-44bd-4400-80f0-7c87a7aa21b3">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/4843321a-8d5a-4d4c-b848-f5892f51794a">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/4790da66-fa2a-4c4c-9218-330c62396720">

<img width="360" height="650" alt="image" src="https://github.com/user-attachments/assets/8fa625e7-2d95-43d7-b961-3eab7cb0ddee">

# Usage Note:
Code in master branch

# This project is intended for educational purposes only, showcasing the potential dangers of certain malware behaviors in a controlled environment.
