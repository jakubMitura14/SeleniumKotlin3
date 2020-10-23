package utils

object Util {
    // Operating systems. from https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
    private var os: OS? = null
    val oS: OS?
        get() {
            if (os == null) {
                val operSys = System.getProperty("os.name").toLowerCase()
                if (operSys.contains("win")) {
                    os = OS.WINDOWS
                } else if (operSys.contains("nix") || operSys.contains("nux")
                        || operSys.contains("aix")) {
                    os = OS.LINUX
                } else if (operSys.contains("mac")) {
                    os = OS.MAC
                } else if (operSys.contains("sunos")) {
                    os = OS.SOLARIS
                }
            }
            return os
        }

    enum class OS {
        WINDOWS, LINUX, MAC, SOLARIS
    }
}