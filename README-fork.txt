Maven / NetBeans branch by
Wolfgang Frisch <wfr@roembden.net>

* Added and fixed Maven pom.xml from dinoboy 
* Removed all bundled JARs -> pure Maven build

* Broken: jruby-readline (no JAR in Maven)
* Broken: jDeskMetrics (no JAR in Maven)
* Broken: Mac OS X specifics (no JAR in Maven)
* Broken: SyncService ("mahalo" library abandoned)

TODO:
- reimplement SyncService
- fix or replace jruby-readline
