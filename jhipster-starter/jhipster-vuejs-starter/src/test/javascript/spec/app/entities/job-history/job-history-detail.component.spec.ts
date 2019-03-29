/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import JobHistoryDetailComponent from '@/entities/job-history/job-history-details.vue';
import JobHistoryClass from '@/entities/job-history/job-history-details.component';
import JobHistoryService from '@/entities/job-history/job-history.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('Component Tests', () => {
  describe('JobHistory Management Detail Component', () => {
    let wrapper: Wrapper<JobHistoryClass>;
    let comp: JobHistoryClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<JobHistoryClass>(JobHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { jobHistoryService: () => new JobHistoryService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveJobHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.jobHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
