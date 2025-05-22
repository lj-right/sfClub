package com.jingdiansuifeng.subject.domain.service.impl;

import com.jingdiansuifeng.subject.domain.service.SubjectMappingService;
import com.jingdiansuifeng.subject.domain.entity.SubjectMapping;
import com.jingdiansuifeng.subject.domain.mapper.SubjectMappingDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类关系表(SubjectMapping)表服务实现类
 *
 */
@Service("subjectMappingService")
public class SubjectMappingServiceImpl implements SubjectMappingService {

    @Resource
    private SubjectMappingDao subjectMappingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param  id 主键
     * @return 实例对象
     */
    @Override
    public SubjectMapping queryById(int id) {
        return this.subjectMappingDao.queryById(id);
    }



    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.insert(subjectMapping);
    }

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.update(subjectMapping);
    }

    /**
     * 通过主键删除数据
     *
     * @param  id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(int id) {
        return this.subjectMappingDao.deleteById(id) > 0;
    }

    /**
     * 查询标签id
     * @param subjectMapping
     * @return
     */
    @Override
    public List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.queryDistinctLabelId(subjectMapping);
    }

    /**
     * 批量插入
     * @param mappingList
     */
    @Override
    public void batchInsert(List<SubjectMapping> mappingList) {
        this.subjectMappingDao.insertBatch(mappingList);
    }
}
