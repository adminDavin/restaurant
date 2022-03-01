/**
 * Augment the typings of Vue.js
 */

import Vue from 'vue'
import { GlobalUtil } from '~/utils/globalUtil'
import { request } from 'http';
declare module 'sha1'

declare module 'vue/types/vue' {
  interface Vue {
    isMobile(): Boolean
    jump(to: any):void
    back():void
    redirect(to: any): void
    fullScreen(): void
    msgShow(text: string, type?: string, duration?: number): void
    confirm(config: any): Promise<any>
    arr2D(array: Array<any>, full: Boolean, cols: number): Array<any>
    proxy(url: string, method?: string, params?: any): Promise<any>
    request(config: any): Promise<any>
    date2Str(date: Date): string
    openNewWindow(href: string): void
  }
}

declare module '@nuxt/types' {
  interface Context {
    isMobile(): Boolean
    jump(to: any):void
    back():void
    redirect(to: any): void
    fullScreen(): void
    msgShow(text: string, type?: string, duration?: number): void
    confirm(config: any): Promise<any>
    arr2D(array: Array<any>, full: Boolean, cols: number): Array<any>
    proxy(url: string, method?: string, params?: any): Promise<any>
    request(config: any): Promise<any>
    date2Str(date: Date): string
    openNewWindow(href: string): void
  }
}