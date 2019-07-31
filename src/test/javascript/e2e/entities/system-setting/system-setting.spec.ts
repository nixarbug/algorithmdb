/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SystemSettingComponentsPage, SystemSettingDeleteDialog, SystemSettingUpdatePage } from './system-setting.page-object';

const expect = chai.expect;

describe('SystemSetting e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let systemSettingUpdatePage: SystemSettingUpdatePage;
  let systemSettingComponentsPage: SystemSettingComponentsPage;
  let systemSettingDeleteDialog: SystemSettingDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SystemSettings', async () => {
    await navBarPage.goToEntity('system-setting');
    systemSettingComponentsPage = new SystemSettingComponentsPage();
    await browser.wait(ec.visibilityOf(systemSettingComponentsPage.title), 5000);
    expect(await systemSettingComponentsPage.getTitle()).to.eq('System Settings');
  });

  it('should load create SystemSetting page', async () => {
    await systemSettingComponentsPage.clickOnCreateButton();
    systemSettingUpdatePage = new SystemSettingUpdatePage();
    expect(await systemSettingUpdatePage.getPageTitle()).to.eq('Create or edit a System Setting');
    await systemSettingUpdatePage.cancel();
  });

  it('should create and save SystemSettings', async () => {
    const nbButtonsBeforeCreate = await systemSettingComponentsPage.countDeleteButtons();

    await systemSettingComponentsPage.clickOnCreateButton();
    await promise.all([
      systemSettingUpdatePage.keySelectLastOption(),
      systemSettingUpdatePage.setValueInput('value'),
      systemSettingUpdatePage.setDescriptionInput('description')
    ]);
    expect(await systemSettingUpdatePage.getValueInput()).to.eq('value', 'Expected Value value to be equals to value');
    expect(await systemSettingUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    await systemSettingUpdatePage.save();
    expect(await systemSettingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await systemSettingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SystemSetting', async () => {
    const nbButtonsBeforeDelete = await systemSettingComponentsPage.countDeleteButtons();
    await systemSettingComponentsPage.clickOnLastDeleteButton();

    systemSettingDeleteDialog = new SystemSettingDeleteDialog();
    expect(await systemSettingDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this System Setting?');
    await systemSettingDeleteDialog.clickOnConfirmButton();

    expect(await systemSettingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
