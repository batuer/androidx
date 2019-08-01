package com.gusi.androidx.model;

import android.text.TextUtils;

import androidx.core.util.Pair;

import com.gusi.androidx.model.entity.Project;
import com.gusi.androidx.model.http.Api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @Author ylw 2018/6/21 14:20
 */
public final class DataManager implements IData, Api {
    private Api mApi;

    public DataManager() {}

    public DataManager(Api api) {
        mApi = api;
    }

    @Override
    public Flowable<List<Pair<String, String>>> getRegistration(String url) {
        return Flowable.fromCallable(() -> {
            List<Pair<String, String>> dataIdPairs = new ArrayList(0);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("xmgs_tab");
            if (elements != null && elements.first() != null) {
                Element first = elements.first();
                Elements dataIdElements = first.getElementsByAttribute("data-id");
                for (Element element : dataIdElements) {
                    String text = element.text();
                    dataIdPairs.add(new Pair<>(text, element.attr("data-id")));
                }
            }
            return dataIdPairs;
        });
    }

    @Override
    public Flowable<List<Project>> getProjects(String url) {
        return Flowable.fromCallable(() -> {
            List<Project> projects = new ArrayList<>(10);
            Document document = Jsoup.connect(url).get();
            Elements xmgsItems = document.getElementsByClass("xmgsItem");
            if (xmgsItems.isEmpty()) {
                return projects;
            }
            for (Element xmgsItem : xmgsItems) {
                String projectName = xmgsItem.getElementsByClass("isInlineblock  textOverflow vm xmgsSpan").text();
                Elements overviewNoticeElement =
                    xmgsItem.getElementsByClass("color_e34156 vm mag_l10 size16 isInlineblock " + "mag_b3");
                String overview = overviewNoticeElement.attr("onclick");

                String notice = "";
                if (overviewNoticeElement.size() > 1) {
                    notice = overviewNoticeElement.get(1).attr("onclick");
                }
                String publicResult =
                    xmgsItem.getElementsByClass("color_e34156 vm mag_b3 size16 isInlineblock").attr("onclick");
                String progress = xmgsItem.getElementsByClass("vm mag_l10").attr("onclick");
                Element tableElement = xmgsItem.getElementsByClass("w_100").first();
                String enterprise =
                    tableElement.getElementsByClass("vm isInlineblock " + "max_w426 textOverflow").text().trim();
                String registerTime = tableElement.getElementsByClass("vm max_w353 color_e34156").text().trim();
                String presaleNum = tableElement.getElementsByClass("vm isInlineblock max_w353 wrap").text().trim();
                String receiveMaterial = tableElement.getElementsByClass("vm max_w426").text().trim();
                Elements buildingRecdiveElement =
                    tableElement.getElementsByClass("vm isInlineblock wrap " + "max_w426");
                String buildingNumber = buildingRecdiveElement.first().text().trim();
                String receiveSite = "";
                if (buildingRecdiveElement.size() > 1) {
                    receiveSite = buildingRecdiveElement.get(1).text().trim();
                }
                String phone = xmgsItem.getElementsByClass("pab right_160 top_8 size18 fontGeorgia").first()
                    .getElementsByClass("color_e34156").text().trim();

                Project project = new Project();
                project.setProjectName(projectName.trim());
                project.setOverview(getSubstr(overview, "('", "')"));
                project.setNotice(getSubstr(notice, "content: '", "',scrollbar"));
                project.setPublicResult(getSubstr(publicResult, "location.href='", "';"));
                project.setProgress(getSubstr(progress, "content: '", "',scrollbar"));
                project.setPhone(phone);
                project.setEnterprise(enterprise);
                project.setRegisterTime(registerTime);
                project.setBuildingNum(buildingNumber);
                project.setPresaleNum(presaleNum);
                project.setReceiveTime(receiveMaterial);
                project.setReceiveSite(receiveSite);
                projects.add(project);
            }
            return projects;
        });
    }

    private String getSubstr(String string, String start, String end) {
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
            return "";
        }
        int startIndex = string.indexOf(start);
        int endIndex = end.indexOf(end);
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return "";
        }
        return string.substring(startIndex + start.length(), endIndex).trim();
    }
}
