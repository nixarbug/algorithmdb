/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProblemGroupComponentsPage, ProblemGroupDeleteDialog, ProblemGroupUpdatePage } from './problem-group.page-object';

const expect = chai.expect;

describe('ProblemGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let problemGroupUpdatePage: ProblemGroupUpdatePage;
  let problemGroupComponentsPage: ProblemGroupComponentsPage;
  let problemGroupDeleteDialog: ProblemGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProblemGroups', async () => {
    await navBarPage.goToEntity('problem-group');
    problemGroupComponentsPage = new ProblemGroupComponentsPage();
    await browser.wait(ec.visibilityOf(problemGroupComponentsPage.title), 5000);
    expect(await problemGroupComponentsPage.getTitle()).to.eq('Problem Groups');
  });

  it('should load create ProblemGroup page', async () => {
    await problemGroupComponentsPage.clickOnCreateButton();
    problemGroupUpdatePage = new ProblemGroupUpdatePage();
    expect(await problemGroupUpdatePage.getPageTitle()).to.eq('Create or edit a Problem Group');
    await problemGroupUpdatePage.cancel();
  });

  it('should create and save ProblemGroups', async () => {
    const nbButtonsBeforeCreate = await problemGroupComponentsPage.countDeleteButtons();

    await problemGroupComponentsPage.clickOnCreateButton();
    await promise.all([problemGroupUpdatePage.setNameInput('name')]);
    expect(await problemGroupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await problemGroupUpdatePage.save();
    expect(await problemGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await problemGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProblemGroup', async () => {
    const nbButtonsBeforeDelete = await problemGroupComponentsPage.countDeleteButtons();
    await problemGroupComponentsPage.clickOnLastDeleteButton();

    problemGroupDeleteDialog = new ProblemGroupDeleteDialog();
    expect(await problemGroupDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Problem Group?');
    await problemGroupDeleteDialog.clickOnConfirmButton();

    expect(await problemGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
