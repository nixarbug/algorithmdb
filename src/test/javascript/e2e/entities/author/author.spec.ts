/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AuthorComponentsPage, AuthorDeleteDialog, AuthorUpdatePage } from './author.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Author e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let authorUpdatePage: AuthorUpdatePage;
  let authorComponentsPage: AuthorComponentsPage;
  let authorDeleteDialog: AuthorDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Authors', async () => {
    await navBarPage.goToEntity('author');
    authorComponentsPage = new AuthorComponentsPage();
    await browser.wait(ec.visibilityOf(authorComponentsPage.title), 5000);
    expect(await authorComponentsPage.getTitle()).to.eq('Authors');
  });

  it('should load create Author page', async () => {
    await authorComponentsPage.clickOnCreateButton();
    authorUpdatePage = new AuthorUpdatePage();
    expect(await authorUpdatePage.getPageTitle()).to.eq('Create or edit a Author');
    await authorUpdatePage.cancel();
  });

  it('should create and save Authors', async () => {
    const nbButtonsBeforeCreate = await authorComponentsPage.countDeleteButtons();

    await authorComponentsPage.clickOnCreateButton();
    await promise.all([
      authorUpdatePage.setNameInput('name'),
      authorUpdatePage.setPictureInput(absolutePath),
      authorUpdatePage.setInfoInput('info'),
      authorUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      authorUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await authorUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await authorUpdatePage.getPictureInput()).to.endsWith(
      fileNameToUpload,
      'Expected Picture value to be end with ' + fileNameToUpload
    );
    expect(await authorUpdatePage.getInfoInput()).to.eq('info', 'Expected Info value to be equals to info');
    expect(await authorUpdatePage.getDateCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateCreated value to be equals to 2000-12-31'
    );
    expect(await authorUpdatePage.getDateUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateUpdated value to be equals to 2000-12-31'
    );
    await authorUpdatePage.save();
    expect(await authorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await authorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Author', async () => {
    const nbButtonsBeforeDelete = await authorComponentsPage.countDeleteButtons();
    await authorComponentsPage.clickOnLastDeleteButton();

    authorDeleteDialog = new AuthorDeleteDialog();
    expect(await authorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Author?');
    await authorDeleteDialog.clickOnConfirmButton();

    expect(await authorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
