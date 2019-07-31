/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlgorithmComponentsPage, AlgorithmDeleteDialog, AlgorithmUpdatePage } from './algorithm.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Algorithm e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let algorithmUpdatePage: AlgorithmUpdatePage;
  let algorithmComponentsPage: AlgorithmComponentsPage;
  let algorithmDeleteDialog: AlgorithmDeleteDialog;
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

  it('should load Algorithms', async () => {
    await navBarPage.goToEntity('algorithm');
    algorithmComponentsPage = new AlgorithmComponentsPage();
    await browser.wait(ec.visibilityOf(algorithmComponentsPage.title), 5000);
    expect(await algorithmComponentsPage.getTitle()).to.eq('Algorithms');
  });

  it('should load create Algorithm page', async () => {
    await algorithmComponentsPage.clickOnCreateButton();
    algorithmUpdatePage = new AlgorithmUpdatePage();
    expect(await algorithmUpdatePage.getPageTitle()).to.eq('Create or edit a Algorithm');
    await algorithmUpdatePage.cancel();
  });

  it('should create and save Algorithms', async () => {
    const nbButtonsBeforeCreate = await algorithmComponentsPage.countDeleteButtons();

    await algorithmComponentsPage.clickOnCreateButton();
    await promise.all([
      algorithmUpdatePage.setNameInput('name'),
      algorithmUpdatePage.setInputInput('input'),
      algorithmUpdatePage.setOutputInput('output'),
      algorithmUpdatePage.setIdeaInput('idea'),
      algorithmUpdatePage.setDescriptionInput('description'),
      algorithmUpdatePage.setRealLifeUseInput('realLifeUse'),
      algorithmUpdatePage.setPseudocodeInput('pseudocode'),
      algorithmUpdatePage.setFlowchartInput('flowchart'),
      algorithmUpdatePage.setFlowchartImageInput(absolutePath),
      algorithmUpdatePage.setComplexityAnalysisInput('complexityAnalysis'),
      algorithmUpdatePage.setCorrectnessProofInput('correctnessProof'),
      algorithmUpdatePage.setAverageStarsInput('5'),
      algorithmUpdatePage.setTotalFavsInput('5'),
      algorithmUpdatePage.setWeightedRatingInput('5'),
      algorithmUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      algorithmUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      algorithmUpdatePage.worstCaseComplexitySelectLastOption(),
      algorithmUpdatePage.averageCaseComplexitySelectLastOption(),
      algorithmUpdatePage.bestCaseComplexitySelectLastOption()
      // algorithmUpdatePage.authorSelectLastOption(),
      // algorithmUpdatePage.tagSelectLastOption(),
      // algorithmUpdatePage.problemSelectLastOption(),
    ]);
    expect(await algorithmUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await algorithmUpdatePage.getInputInput()).to.eq('input', 'Expected Input value to be equals to input');
    expect(await algorithmUpdatePage.getOutputInput()).to.eq('output', 'Expected Output value to be equals to output');
    expect(await algorithmUpdatePage.getIdeaInput()).to.eq('idea', 'Expected Idea value to be equals to idea');
    expect(await algorithmUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await algorithmUpdatePage.getRealLifeUseInput()).to.eq('realLifeUse', 'Expected RealLifeUse value to be equals to realLifeUse');
    expect(await algorithmUpdatePage.getPseudocodeInput()).to.eq('pseudocode', 'Expected Pseudocode value to be equals to pseudocode');
    expect(await algorithmUpdatePage.getFlowchartInput()).to.eq('flowchart', 'Expected Flowchart value to be equals to flowchart');
    expect(await algorithmUpdatePage.getFlowchartImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected FlowchartImage value to be end with ' + fileNameToUpload
    );
    expect(await algorithmUpdatePage.getComplexityAnalysisInput()).to.eq(
      'complexityAnalysis',
      'Expected ComplexityAnalysis value to be equals to complexityAnalysis'
    );
    expect(await algorithmUpdatePage.getCorrectnessProofInput()).to.eq(
      'correctnessProof',
      'Expected CorrectnessProof value to be equals to correctnessProof'
    );
    expect(await algorithmUpdatePage.getAverageStarsInput()).to.eq('5', 'Expected averageStars value to be equals to 5');
    expect(await algorithmUpdatePage.getTotalFavsInput()).to.eq('5', 'Expected totalFavs value to be equals to 5');
    expect(await algorithmUpdatePage.getWeightedRatingInput()).to.eq('5', 'Expected weightedRating value to be equals to 5');
    expect(await algorithmUpdatePage.getDateCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateCreated value to be equals to 2000-12-31'
    );
    expect(await algorithmUpdatePage.getDateUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateUpdated value to be equals to 2000-12-31'
    );
    await algorithmUpdatePage.save();
    expect(await algorithmUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await algorithmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Algorithm', async () => {
    const nbButtonsBeforeDelete = await algorithmComponentsPage.countDeleteButtons();
    await algorithmComponentsPage.clickOnLastDeleteButton();

    algorithmDeleteDialog = new AlgorithmDeleteDialog();
    expect(await algorithmDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Algorithm?');
    await algorithmDeleteDialog.clickOnConfirmButton();

    expect(await algorithmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
