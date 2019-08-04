package com.gusi.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author ylw 2019/8/4 20:38
 * @Des
 */
public class MyClass {
    public static void main(String[] args) {
        try {
             Document document = Jsoup.connect("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgsca.aspx?state=-1&page=1").get();
//            Document document = Jsoup.connect("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgs.aspx?state=-1&page=1").get();
            Elements xmgsItems = document.getElementsByClass("xmgsItem");
            Element first = xmgsItems.first();
            String projectName = first.getElementsByClass("isInlineblock  textOverflow vm xmgsSpan").text();
            String overview =
                first.getElementsByClass("color_e34156 vm mag_l10 size16 isInlineblock mag_b3").attr("onclick");
            String notice =
                first.getElementsByClass("color_e34156 vm mag_l10 size16 isInlineblock mag_b3").get(1).attr("onclick");
            String publicResult =
                first.getElementsByClass("color_e34156 vm mag_b3 size16 isInlineblock").attr("onclick");
            String progress = first.getElementsByClass("vm mag_l10").attr("onclick");
            Element tableElement = first.getElementsByClass("w_100").first();
            String enterprise =
                tableElement.getElementsByClass("vm isInlineblock " + "max_w426 textOverflow").text().trim();
            String registerTime = tableElement.getElementsByClass("vm max_w353 color_e34156").text().trim();
            String presaleNum = tableElement.getElementsByClass("vm isInlineblock max_w353 wrap").text().trim();
            String receiveMaterial = tableElement.getElementsByClass("vm max_w426").text().trim();
            String buildingNumber =
                tableElement.getElementsByClass("vm isInlineblock wrap max_w426").first().text().trim();
            String receiveSite = tableElement.getElementsByClass("vm isInlineblock wrap max_w426").get(1).text().trim();

            Project project = new Project();
            project.setProjectName(projectName.trim());
            project.setOverview(overview.substring(overview.indexOf("('") + 2, overview.indexOf("')")).trim());
            project
                .setNotice(notice.substring(notice.indexOf("content: '") + 10, notice.indexOf("',scrollbar")).trim());
            String trim =
                publicResult.substring(publicResult.indexOf("location.href='") + ("location" + ".href='").length(),
                    publicResult.indexOf("';")).trim();
            project.setPublicResult(trim);
            project.setProgress(progress
                .substring(progress.indexOf("content: '") + "content: '".length(), progress.indexOf("',scrollbar"))
                .trim());
            project.setPhone(first.getElementsByClass("pab right_160 top_8 size18 fontGeorgia").first()
                .getElementsByClass("color_e34156").text().trim());
            project.setEnterprise(enterprise);
            project.setRegisterTime(registerTime);
            project.setBuildingNum(buildingNumber);
            project.setPresaleNum(presaleNum);
            project.setReceiveMaterial(receiveMaterial);
            project.setReceiveSite(receiveSite);
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
