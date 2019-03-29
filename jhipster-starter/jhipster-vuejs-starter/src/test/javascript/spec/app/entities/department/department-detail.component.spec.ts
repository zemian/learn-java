/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import * as config from '@/shared/config/config';
import DepartmentDetailComponent from '@/entities/department/department-details.vue';
import DepartmentClass from '@/entities/department/department-details.component';
import DepartmentService from '@/entities/department/department.service';

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
  describe('Department Management Detail Component', () => {
    let wrapper: Wrapper<DepartmentClass>;
    let comp: DepartmentClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      wrapper = shallowMount<DepartmentClass>(DepartmentDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { departmentService: () => new DepartmentService() }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', async () => {
      it('Should call load all on init', async () => {
        // GIVEN
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

        // WHEN
        comp.retrieveDepartment(123);
        await comp.$nextTick();

        // THEN
        expect(comp.department).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
