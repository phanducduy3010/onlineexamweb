var listExam = [];
class Exam {
    // async getAll("http://localhost:8089/classes") {}

    async getExamsInClass(id) {
        try {
            listExam = await getAll(`http://localhost:8089/classes/${id}/exams`);
            return listExam;
        } catch (ex) {

        }
        return listExam;
    }

    async getExamById(id) {
        try {
            var exam = await getAll(`http://localhost:8089/exams/${id}`);
            return exam;
        } catch (ex) {

        }
    }

    async getQuesInExam(id) {
        try {
            var listQues = await getAll(`http://localhost:8089/exams/${id}/questions`);
            return listQues;
        } catch (ex) {

        }
    }
}


class Result {
    async postResult(param) {
        try {
            const res = await postData("http://localhost:8089/results", param);
            return res;
        } catch (ex) {

        }
    }
}