/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('member_log', {
    'id': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'memberId': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      comment: "null",
      references: {
        model: 'member',
        key: 'id'
      }
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
      type: DataTypes.INTEGER(11),
      allowNull: false,
      comment: "null",
      references: {
        model: 'job',
        key: 'id'
      }
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
