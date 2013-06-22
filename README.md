drozer Community
================

drozer is the leading security assessment framework for Android.

The Android sandbox is designed to restrict the access of an unprivileged
application to other applications and the underlying device, without requesting
appropriate permissions.

drozer allows you to assume the role of an application on a device, to see
what you can access. You will be surprised how much access you actually have...

drozer facilitates investigation, exploitation and provides tools for post
exploitation.

drozer is open source software, maintained by MWR InfoSecurity, and can be
downloaded from the project site:

    mwr.to/drozer


Rogue Agent
-----------

The drozer Rogue Agent is designed for use as a RAT. Once deployed to an
Android device, it will establish a connection back to the drozer Server set in
res/raw/endpoint.txt.

The drozer Rogue Agent can be activated in three ways:

  - by restarting the device; the Agent responds to the BOOT_COMPLETED
    broadcast event and establishes a connection*.
  - using am from an Android shell: am startservice -n com.mwr.dz/.Agent**
  - using am to send a PWN broadcast: am broadcast -a com.mwr.dz.PWN*

 *  on Android 3.1+, the agent must have been previously started using the am
    startservice command
**  on Android <3.1, this command is not available, the PWN broadcast should be
    used instead 


License
-------

drozer is released under a 3-clause BSD License.
See LICENSE for full details.


Contacting the Project
----------------------

The public source repository for drozer is hosted on GitHub:

  https://github.com/mwrlabs/drozer

Bug reports, feature requests, comments and questions can be submitted through
the GitHub Issues feature, or sent to:

  drozer.oss [at] mwrinfosecurity.com

Follow the drozer Project on Twitter:

  @droidhg

