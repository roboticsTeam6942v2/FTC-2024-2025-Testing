1. plug everything in.
2. config everything using names at end of file
3. do not leave the robot in the closed position to prevent damage, the first try, start it in the open position
4. try to run FindLimitsV2.java, if for some reason that doesn't work, use FindLimits
5. assuming you got it running, test that the motor rotation directions are correct, if they are not, stop the program and change it in org.firstinspires.ftc.teamcode.opmodes.remoteDirections.Constants
6. while youre running tests, see if the motors move fluidly, if they are too jumpy or slow go to Constants and change the BOOSTER values accordingly. Reduce if jumpy, increase if slow
7. while youre still running tests, check to see if the servos move enough, increase STEP if they do not, or decrease them accordingly
8. if you made any changes to the constants in steps 5-7, kill the code and rebuild it
9. attempt to curl the robot into its starting position, once you get it, take the value of "shoulder" and "elbow" from the driverstation and the inverse of them in the Constants file under "shoulderOpen" and "elbowOpen". In other words if theyre positive make them negative and if theyre negative make them positive. Also note the wrist starting position as wristInitial
10. after you put that in the constants file, rebuild the code and rerun it this time starting from the closed position, if all goes well the robot will open itself up
11. from here please raise the arm slightly so that you can attempt to find servo positions, for fingers find an open/item grabbed position, and for wrist, give me a pickup position, and place position, write these in Constants, you can figure out the variable names, you do not need to rebuild the code
12. now get me the motor positions for the hovering to grab, grabbing, low basket, and high basket positions. Look in Constants at all of the position.put within static{
13. after all that, make sure drivetrain motors are configured to frontLeft, frontRight, backLeft, and backRight then run TeleopV2 or Teleop and have a ball


CONTROLS (all on gamepad1):
    - Shoulder, left joystick y
    - Elbow, right joystick y
    - Fingers, dpad up and down
    - Wrist, a and y button



CONFIG NAMES (I will let you logic your way through this, my hint it to imagine an arm):

    Motors:
        - elbowMotor
        - shoulderMotorLeft
        - shoulderMotorRight

    Servos:
        - wrist
        - fingers (i used plural to make it sound less weird but it didn't help)

Try to run FindLimitsV2, if for some reason that doesn't work, use FindLimits