/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EmployeeComponent from '@/entities/employee/employee.vue';
import EmployeeClass from '@/entities/employee/employee.component';
import EmployeeService from '@/entities/employee/employee.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

jest.mock('axios', () => ({
  get: jest.fn(),
  delete: jest.fn()
}));

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('Employee Management Component', () => {
    let wrapper: Wrapper<EmployeeClass>;
    let comp: EmployeeClass;

    beforeEach(() => {
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({ headers: {} }));

      wrapper = shallowMount<EmployeeClass>(EmployeeComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          employeeService: () => new EmployeeService()
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      mockedAxios.get.mockReturnValue(Promise.resolve({ headers: {}, data: [{ id: 123 }] }));

      // WHEN
      comp.retrieveAllEmployees();
      await comp.$nextTick();

      // THEN
      expect(mockedAxios.get).toHaveBeenCalled();
      expect(comp.employees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      mockedAxios.get.mockReturnValue(Promise.resolve({ headers: {}, data: [{ id: 123 }] }));

      // WHEN
      comp.loadPage(1);
      await comp.$nextTick();

      // THEN
      expect(mockedAxios.get).toHaveBeenCalled();
      expect(comp.employees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      mockedAxios.get.mockReset();
      mockedAxios.get.mockReturnValue(Promise.resolve({ headers: {}, data: [{ id: 123 }] }));

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(mockedAxios.get).toHaveBeenCalledTimes(2);
      expect(comp.page).toEqual(1);
      expect(comp.employees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });

    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      mockedAxios.delete.mockReturnValue(Promise.resolve({}));
      mockedAxios.get.mockReturnValue(Promise.resolve({}));

      // WHEN
      comp.prepareRemove({ id: 'test' });
      comp.removeEmployee();
      await comp.$nextTick();

      // THEN
      expect(mockedAxios.delete).toHaveBeenCalled();
      expect(mockedAxios.get).toHaveBeenCalled();
    });
  });
});
