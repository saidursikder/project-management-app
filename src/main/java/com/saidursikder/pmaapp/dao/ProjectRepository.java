package com.saidursikder.pmaapp.dao;

import com.saidursikder.pmaapp.dto.ChartData;
import com.saidursikder.pmaapp.dto.TimeChartData;
import com.saidursikder.pmaapp.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    public List<Project> findAll();

    @Query(nativeQuery = true, value = "SELECT stage AS label, COUNT(*) AS value " +
            "FROM project " +
            "GROUP BY stage")
    public List<ChartData> getProjectStatus();

    @Query(nativeQuery = true, value = "SELECT name as projectName, start_date as startDate, end_date as endDate " +
                                "FROM project WHERE start_date is not null")
    public List<TimeChartData> getTimeData();

    public Project findByProjectId(long theId);
}
