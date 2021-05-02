import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8088/login')

WebUI.setText(findTestObject('Object Repository/Page_Booker - Login/input_Email_username'), 'adrien.paysant@utbm.fr')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Booker - Login/input_Password_password'), 'Dw434Hy1yb4=')

WebUI.sendKeys(findTestObject('Object Repository/Page_Booker - Login/input_Password_password'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Page_Booker/a_Logout'))

WebUI.setText(findTestObject('Object Repository/Page_Booker - Login/input_Email_username'), 'lasardinejohn@gmail.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Booker - Login/input_Password_password'), 'Dw434Hy1yb4=')

WebUI.sendKeys(findTestObject('Object Repository/Page_Booker - Login/input_Password_password'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Page_Booker/a_Add Book'))

WebUI.setText(findTestObject('Object Repository/Page_Booker/input_Title _title'), 'lucien')

WebUI.setText(findTestObject('Object Repository/Page_Booker/textarea_Description _description'), 'je suis une description')

WebUI.setText(findTestObject('Object Repository/Page_Booker/input_Edition _edition'), 'lucien edition')

WebUI.click(findTestObject('Object Repository/Page_Booker/input_Add the cover image _btn btn-primary'))

