from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
from com.android.monkeyrunner.easy import EasyMonkeyDevice, By
device = MonkeyRunner.waitForConnection('CB512FSJWB')
device.installPackage('/Users/.../app-release.apk')
package = 'com.package.name'
activity = 'MainActivity'
runComponent = package + '/' + activity
device.startActivity(component=runComponent)
easy_device = EasyMonkeyDevice(device)
# com.android.packageinstaller:id/permission_allow_button
easy_device.touch(By.id('permission_allow_button'),MonkeyDevice.DOWN_AND_UP)
easy_device.touch(By.id('imageView'),MonkeyDevice.DOWN_AND_UP)
# result = device.takeSnapshot()
# result.writeToFile('/Users/.../snowy/try/shot1.png','png')