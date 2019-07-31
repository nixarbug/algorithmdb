/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FunctionClassComponentsPage, FunctionClassDeleteDialog, FunctionClassUpdatePage } from './function-class.page-object';

const expect = chai.expect;

describe('FunctionClass e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let functionClassUpdatePage: FunctionClassUpdatePage;
  let functionClassComponentsPage: FunctionClassComponentsPage;
  let functionClassDeleteDialog: FunctionClassDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FunctionClasses', async () => {
    await navBarPage.goToEntity('function-class');
    functionClassComponentsPage = new FunctionClassComponentsPage();
    await browser.wait(ec.visibilityOf(functionClassComponentsPage.title), 5000);
    expect(await functionClassComponentsPage.getTitle()).to.eq('Function Classes');
  });

  it('should load create FunctionClass page', async () => {
    await functionClassComponentsPage.clickOnCreateButton();
    functionClassUpdatePage = new FunctionClassUpdatePage();
    expect(await functionClassUpdatePage.getPageTitle()).to.eq('Create or edit a Function Class');
    await functionClassUpdatePage.cancel();
  });

  it('should create and save FunctionClasses', async () => {
    const nbButtonsBeforeCreate = await functionClassComponentsPage.countDeleteButtons();

    await functionClassComponentsPage.clickOnCreateButton();
    await promise.all([
      functionClassUpdatePage.setNameInput('name'),
      functionClassUpdatePage.setFormulaInput('formula'),
      functionClassUpdatePage.setRelativeOrderInput('5')
    ]);
    expect(await functionClassUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await functionClassUpdatePage.getFormulaInput()).to.eq('formula', 'Expected Formula value to be equals to formula');
    expect(await functionClassUpdatePage.getRelativeOrderInput()).to.eq('5', 'Expected relativeOrder value to be equals to 5');
    await functionClassUpdatePage.save();
    expect(await functionClassUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await functionClassComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FunctionClass', async () => {
    const nbButtonsBeforeDelete = await functionClassComponentsPage.countDeleteButtons();
    await functionClassComponentsPage.clickOnLastDeleteButton();

    functionClassDeleteDialog = new FunctionClassDeleteDialog();
    expect(await functionClassDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Function Class?');
    await functionClassDeleteDialog.clickOnConfirmButton();

    expect(await functionClassComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
