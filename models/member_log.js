/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('member_log', {
    'id': {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'memberId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'firstName': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'lastName': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'email': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'phone': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'jobId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'createdAt': {
      type: DataTypes.DATE,
      allowNull: false,
      comment: "null"
    },
    'updatedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    },
    'deletedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    }
  }, {
    tableName: 'member_log',
    paranoid: true
  });
};
