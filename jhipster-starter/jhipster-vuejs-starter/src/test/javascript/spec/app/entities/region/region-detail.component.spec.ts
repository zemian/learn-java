/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import RegionDetailComponent from '@/entities/region/region-details.vue';
import RegionClass from '@/entities/region/region-details.component';
import RegionService from '@/entities/region/region.service';

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
  describe('Region Management Detail Component', () => {
    let wrapper: Wrapper<RegionClass>;
    let comp: RegionClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<RegionClass>(RegionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { regionService: () => new RegionService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveRegion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.region).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
