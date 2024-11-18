package edu.student_orden.dao;

import edu.student_orden.config.Config;
import edu.student_orden.domain.wedding.*;
import edu.student_orden.exaption.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentOrderDaoImpl implements StudentOrderDao
{
        private static final String INSERT_ORDER =
                "INSERT INTO jc_student_order("+
                        "student_order_status, student_order_date, " +
                        "h_sur_name, h_given_name, h_patronymic, h_date_of_birthday, " +
                        "h_passport_seria, h_passport_number, h_passport_date, h_passport_office_id, " +
                        "h_post_index, h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_id," +
                        " w_sur_name, w_given_name, w_patronymic, w_date_of_birthday, w_passport_seria, w_passport_number, " +
                        "w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, " +
                        "w_extension, w_apartment ,w_university_id, w_student_id,certificate_id, register_office_id, marriage_date)" +
                        "VALUES (?, ?," +
                        "?, ?, ?, ?," +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?," +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?," +
                        "?, ?, ?, ?, ?);";

    private static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(student_order_id, c_sur_name, c_given_name," +
                    " c_patronymic, c_date_of_birthday, c_certificate_number, c_certificate_date," +
                    " c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartment)" +
                    " VALUES (?, ?, ?, ?, ?,  ?, ?, ?,?, ?, ?, ?, ?);";
    private static final String SELECT_ORDER =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name , " +
                    "po_h.p_office_area_id as h_p_office_area_id," +
                    "po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, " +
                    "po_w.p_office_name as w_p_office_name  " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "WHERE student_order_status = ? ORDER BY student_order_date";
    private static final String SELECT_CHILD =
             "SELECT soc.*, ro.r_office_area_id, ro.r_office_name " +
                    "FROM jc_student_child soc " +
                    "INNER JOIN  jc_register_office ro ON ro.r_office_id = soc.c_register_office_id " +
                    "WHERE soc.student_order_id IN ";


    private Connection getConnection() throws SQLException {
        return DictionaryDaoImplConnection.getConn();
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        Long result = -1L;
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})) {
            conn.setAutoCommit(false); // управляем транзакцией вручную

            try {
                // Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                //Husband and wife
                setParamForAdult(stmt, 3, so.getHusband());
                setParamForAdult(stmt,18, so.getWife());
                // Marriage
                stmt.setString(33, so.getMarriageCertificateID());
                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
                stmt.setDate(35, Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();
                ResultSet gkRs = stmt.getGeneratedKeys();
                if (gkRs.next()) {
                   result = gkRs.getLong(1);
                }
                gkRs.close(); // на всякий случай
                saveChildren (conn, so, result);
                conn.commit(); // Если транзакция прошла, изменяем в базе данных

            } catch (SQLException e) {
                conn.rollback(); // Вернуьт транзакцию при сбое
                throw e;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrder() throws DaoException {
        List<StudentOrder> results = new LinkedList<>();



        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_ORDER))
        {
            statement.setInt(1,StudentOrderStatus.START.ordinal());

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                StudentOrder so = new StudentOrder();
                fillResults (rs,so);
                fillMarriage (rs, so);
                Adult husband = fillAdult ("h_", rs);
                so.setHusband(husband);
                Adult wife = fillAdult ("w_", rs);
                so.setWife(wife);
                results.add(so);

            }

            findChildren(connection,results );

        rs.close();
        } catch (Exception ex) {
            throw new DaoException(ex);
        }

        return results;
    }

    private void findChildren(Connection connection, List<StudentOrder> results) throws SQLException {
           String cl= "(" + results.stream().map(so-> String.valueOf(so.getStudentOrderId())).
                    collect(Collectors.joining(",")) +")";

           try (PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + cl)){
                ResultSet resultSet =statement.executeQuery();
                while (resultSet.next()){
                    System.out.println(resultSet.getLong(1) + ":" + resultSet.getString(3));
                }
           }

    }

    private void saveChildren(Connection conn, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement((INSERT_CHILD))) {
            int counter = 0;
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamForChild(stmt,child);
                stmt.addBatch(); // Копим заявки, что бы отправить одним BATch, для более быстрой записи. по одному файлу долго
                counter++; //счётчик для подсчёта заявок, для отправки по достижению определённого колличества
                if (counter > 1){
                    stmt.executeBatch();
                    counter = 0;
                }
            }
            if (counter > 0) {
                stmt.executeBatch(); // Отправляем заявки если в конце сеанса не набралось нужного колличества
            }
        }
    }

    private void setParamForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start +4, adult.getPassportSeria() );
        stmt.setString(start +5, adult.getPassportNumber());
        stmt.setDate(  start +6, Date.valueOf(adult.getIssueDate()) );
        stmt.setLong(  start +7, adult.getIssueDepartment().getOfficeId());
        setParamForAddress(stmt, start+8, adult);
        stmt.setLong(start+13, adult.getUniversity().getUniversityId());
        stmt.setString(start+14, adult.getStudentId());
    }

    private void setParamForChild(PreparedStatement stmt, Child child) throws  SQLException{
        setParamsForPerson(stmt,2,child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(  7,  Date.valueOf(child.getIssueDate()) );
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamForAddress(stmt,9,child);
    }
    private static void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start +0, person.getSurName());
        stmt.setString(start +1, person.getGivenName());
        stmt.setString(start +2, person.getPatronymic());
        stmt.setDate(  start +3, Date.valueOf(person.getDateOfBirth()));
    }
    private void setParamForAddress (PreparedStatement stmt, int start, Person person) throws SQLException {
        Address addult_address = person.getAddress();
        stmt.setString(start +0,  addult_address.getPostCode());
        stmt.setLong(  start +1,  addult_address.getStreet().getStreetCode());
        stmt.setString(start +2, addult_address.getBuilding());
        stmt.setString(start +3, addult_address.getExtension());
        stmt.setString(start +4, addult_address.getApartment());
    }
    private Adult fillAdult(String pref, ResultSet rs) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(rs.getString(pref + "sur_name"));
        adult.setGivenName(rs.getString(pref +  "given_name"));
        adult.setPatronymic(rs.getString(pref + "patronymic"));
        adult.setDateOfBirth(rs.getDate(pref + "date_of_birthday").toLocalDate());
        adult.setPassportSeria(rs.getString(pref + "passport_seria"));
        adult.setPassportNumber(rs.getString(pref + "passport_number"));
        adult.setIssueDate(rs.getDate(pref + "passport_date").toLocalDate());

        Long poId = rs.getLong(pref + "passport_office_id");
        String poArea = rs.getString(pref + "p_office_area_id");
        String poName = rs.getString(pref + "p_office_name");
        PassportOffice ps = new PassportOffice(poId, poArea,
                poName );
        adult.setIssueDepartment(ps);
        Address address = new Address();
        address.setPostCode(rs.getString(pref + "post_index"));
        address.setBuilding(rs.getString(pref + "building"));
        address.setExtension(rs.getString(pref  + "extension"));
        address.setApartment(rs.getString(pref + "apartment"));
        Street st = new Street(rs.getLong(pref + "street_code"), "");
        address.setStreet(st);
        adult.setAddress(address);

        University university = new University(rs.getLong(pref + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentId(rs.getString(pref + "student_id"));
        return adult;
    }

    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
        so.setMarriageCertificateID(rs.getString("certificate_id"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
        Long roID = rs.getLong("register_office_id");
        String name = rs.getString("r_office_name");
        String areaID = rs.getString("r_office_area_id");

        RegisterOffice ro = new RegisterOffice(roID,areaID,name);
        so.setMarriageOffice(ro);

    }

    private void fillResults(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderId(rs.getLong("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));

    }
}
