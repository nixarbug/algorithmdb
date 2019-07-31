/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ImplementationComponentsPage, ImplementationDeleteDialog, ImplementationUpdatePage } from './implementation.page-object';

const expect = chai.expect;

describe('Implementation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let implementationUpdatePage: ImplementationUpdatePage;
  let implementationComponentsPage: ImplementationComponentsPage;
  let implementationDeleteDialog: ImplementationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Implementations', async () => {
    await navBarPage.goToEntity('implementation');
    implementationComponentsPage = new ImplementationComponentsPage();
    await browser.wait(ec.visibilityOf(implementationComponentsPage.title), 5000);
    expect(await implementationComponentsPage.getTitle()).to.eq('Implementations');
  });

  it('should load create Implementation page', async () => {
    await implementationComponentsPage.clickOnCreateButton();
    implementationUpdatePage = new ImplementationUpdatePage();
    expect(await implementationUpdatePage.getPageTitle()).to.eq('Create or edit a Implementation');
    await implementationUpdatePage.cancel();
  });

  it('should create and save Implementations', async () => {
    const nbButtonsBeforeCreate = await implementationComponentsPage.countDeleteButtons();

    await implementationComponentsPage.clickOnCreateButton();
    await promise.all([
      implementationUpdatePage.setNameInput('name'),
      implementationUpdatePage.setCodeInput('code'),
      implementationUpdatePage.setNoteInput('note'),
      implementationUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      implementationUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      implementationUpdatePage.languageSelectLastOption(),
      implementationUpdatePage.algorithmSelectLastOption()
    ]);
    expect(await implementationUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await implementationUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await implementationUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
    expect(await implementationUpdatePage.getDateCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateCreated value to be equals to 2000-12-31'
    );
    expect(await implementationUpdatePage.getDateUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateUpdated value to be equals to 2000-12-31'
    );
    await implementationUpdatePage.save();
    expect(await implementationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await implementationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last Implementation', async () => {
    const nbButtonsBeforeDelete = await implementationComponentsPage.countDeleteButtons();
    await implementationComponentsPage.clickOnLastDeleteButton();

    implementationDeleteDialog = new ImplementationDeleteDialog();
    expect(await implementationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Implementation?');
    await implementationDeleteDialog.clickOnConfirmButton();

    expect(await implementationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
