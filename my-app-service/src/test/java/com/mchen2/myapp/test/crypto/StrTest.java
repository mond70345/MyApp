package com.mchen2.myapp.test.crypto;

import org.junit.jupiter.api.Test;

public class StrTest {

    @Test
    public void test() {
        String str= "1.红光定位系统(外部加装对焦)  产品线联系人:黄泽敏                        \n" +
                "2.160镜头 打标范围100*100mm\n" +
                "3.分体机(不含1.显示器2.计算机 )，排插*5条(一台需要一条)，U盤需要五個                     \n" +
                "4.依据23-ITDKH-HW-TW-SZ-07一样配置，控制卡要使用3600EMCC\n" +
                "5.主机电源线须增加到5米，检测铝片要黑色200片\n" +
                "6.新机需发机器前开通二次开发授权，软件永久授权\n" +
                "7.加密狗軟件序號截圖與機器標示牌(黑色)對應資料，解鎖定期巡检，取消每年巡检\n" +
                "8. 红光定位系统(外部加装对焦)，紅光預覽功能，需要一台一個U盤，總共五個U盤\n" +
                "9. 另外發貨:配件增加:IPG-20激光器一套，振鏡方頭兩套(另外發貨)";
        System.out.println(str);
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println();
            System.out.println("每轮排序:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 92, 22, 11, 90};

        System.out.println("排序前的数组为:");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        bubbleSort(arr);

        System.out.println("\n 排序后的数组为:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
