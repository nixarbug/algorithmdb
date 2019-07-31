import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'algorithm',
        loadChildren: './algorithm/algorithm.module#AlgorithmdbAlgorithmModule'
      },
      {
        path: 'system-setting',
        loadChildren: './system-setting/system-setting.module#AlgorithmdbSystemSettingModule'
      },
      {
        path: 'function-class',
        loadChildren: './function-class/function-class.module#AlgorithmdbFunctionClassModule'
      },
      {
        path: 'language',
        loadChildren: './language/language.module#AlgorithmdbLanguageModule'
      },
      {
        path: 'implementation',
        loadChildren: './implementation/implementation.module#AlgorithmdbImplementationModule'
      },
      {
        path: 'author',
        loadChildren: './author/author.module#AlgorithmdbAuthorModule'
      },
      {
        path: 'tag',
        loadChildren: './tag/tag.module#AlgorithmdbTagModule'
      },
      {
        path: 'problem',
        loadChildren: './problem/problem.module#AlgorithmdbProblemModule'
      },
      {
        path: 'problem-group',
        loadChildren: './problem-group/problem-group.module#AlgorithmdbProblemGroupModule'
      },
      {
        path: 'blog-entry',
        loadChildren: './blog-entry/blog-entry.module#AlgorithmdbBlogEntryModule'
      },
      {
        path: 'blog',
        loadChildren: './blog/blog.module#AlgorithmdbBlogModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AlgorithmdbEntityModule {}
