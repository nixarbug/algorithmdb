/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProblemComponentsPage, ProblemDeleteDialog, ProblemUpdatePage } from './problem.page-object';

const expect = chai.expect;

describe('Problem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let problemUpdatePage: ProblemUpdatePage;
  let problemComponentsPage: ProblemComponentsPage;
  let problemDeleteDialog: ProblemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Problems', async () => {
    await navBarPage.goToEntity('problem');
    problemComponentsPage = new ProblemComponentsPage();
    await browser.wait(ec.visibilityOf(problemComponentsPage.title), 5000);
    expect(await problemComponentsPage.getTitle()).to.eq('Problems');
  });

  it('should load create Problem page', async () => {
    await problemComponentsPage.clickOnCreateButton();
    problemUpdatePage = new ProblemUpdatePage();
    expect(await problemUpdatePage.getPageTitle()).to.eq('Create or edit a Problem');
    await problemUpdatePage.cancel();
  });

  it('should create and save Problems', async () => {
    const nbButtonsBeforeCreate = await problemComponentsPage.countDeleteButtons();

    await problemComponentsPage.clickOnCreateButton();
    await promise.all([
      problemUpdatePage.setNameInput('name'),
      problemUpdatePage.setDescriptionInput('description'),
      problemUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      problemUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
      // problemUpdatePage.problemGroupSelectLastOption(),
    ]);
    expect(await problemUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await problemUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await problemUpdatePage.getDateCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateCreated value to be equals to 2000-12-31'
    );
    expect(await problemUpdatePage.getDateUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateUpdated value to be equals to 2000-12-31'
    );
    await problemUpdatePage.save();
    expect(await problemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await problemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Problem', async () => {
    const nbButtonsBeforeDelete = await problemComponentsPage.countDeleteButtons();
    await problemComponentsPage.clickOnLastDeleteButton();

    problemDeleteDialog = new ProblemDeleteDialog();
    expect(await problemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Problem?');
    await problemDeleteDialog.clickOnConfirmButton();

    expect(await problemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
